.PHONY: help
.DEFAULT_GOAL := help

help:
	@echo "---------------------------------------------------------------------------------------"
	@echo ""
	@echo "				CLI"
	@echo ""
	@echo "---------------------------------------------------------------------------------------"
	@echo ""
	@awk 'BEGIN {FS = ":.*##"; printf "Usage: make \033[36m<target>\033[0m\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-25s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

##@ Development
pull: ## Pull latest changes of the project branch
	git submodule update --init --recursive && git submodule update --remote && git pull

docs: ## Build the project documentation
	IS_DOCS=true \
	./cicd/ci/build.sh

build: ## Build the project
	./cicd/ci/build.sh

full-build: ## Full build the project
	@read -p "Sonar Login: " passwd; \
	CI_SECURE_ENV_VARS=true \
	SONAR_ORGANIZATION=bhuwanupadhyay \
	SONAR_HOST=https://sonarcloud.io \
	SONAR_LOGIN=$$passwd \
	./cicd/ci/build.sh

##@ Releasing

version: ## Get the current version
	./cicd/ci/before_ci.sh

release: ## Perform release
	@read -p "Sonatype Password: " passwd; \
	CI_SECURE_ENV_VARS=true \
	PULL_REQUEST=false \
	SONATYPE_USER=developerbhuwan \
	SONATYPE_PASSWORD=$$passwd \
	./cicd/ci/build.sh

rollback: ## Rollback release
	IS_ROLLBACK=true \
	./cicd/ci/build.sh

##@ Deployment
deploy: ## Deploy in docker swarm
	docker stack deploy --compose-file docker-compose.stack.yml highly-scalable-db
list: ## List of services from docker swarm
	docker stack services highly-scalable-db
watch: ## Watch list of services from docker swarm
	watch "docker stack services highly-scalable-db"
logs: ## Logs of service from docker swarm
	@read -p "Service Name: " serviceName; \
	docker service logs --follow highly-scalable-db_$$serviceName
inspect: ## Inspect of network from docker swarm
	@read -p "Network Name: " networkName; \
	docker network inspect highly-scalable-db_$$networkName
destroy: ## Destroy deployment in docker swarm
	docker stack rm highly-scalable-db