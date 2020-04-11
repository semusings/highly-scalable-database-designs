import {FormGroup} from '@angular/forms';
import {InjectionToken} from '@angular/core';

export class BasicFormComponent {

  form: FormGroup;
  serverErrors: Array<ErrorResponse> = [];
  hasServerErrors: boolean = false;

  async onSubmit() {

  }
}

export interface MessageResponse {
  lang: string;
  value: string;
}

export interface ErrorResponse {
  errorId: string;
  translations: Array<MessageResponse>;
}

export interface ServerResponse<T> {
  status: number;
  resources: T;
  errors: Array<ErrorResponse>;
}

export interface AlreadyExistsResponse {
  exists: boolean;
}

export interface LoginResponse {
  status: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface SignUpRequest {
  email: string;
  name: string;
  password: string;
}

export const AUTH_BASE_PATH: InjectionToken<string> = new InjectionToken<string>('UAA service base path.');
