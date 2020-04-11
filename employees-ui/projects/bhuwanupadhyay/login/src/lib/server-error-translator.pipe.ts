import {Pipe, PipeTransform} from '@angular/core';
import {ErrorResponse} from './model';
import { Optional } from '@bhuwanupadhyay/utils';

@Pipe({
  name: 'serverErrorTranslator'
})
export class ServerErrorTranslatorPipe implements PipeTransform {

  transform(value: ErrorResponse, ...args: unknown[]): unknown {
    return Optional.of(value)
      .map(v => v.translations)
      .map(v => v.find(msg => msg.lang === 'en'))
      .map(v => v.value)
      .orElseGet(() => `No translation given by server for errorId: ${value.errorId}`);
  }

}
