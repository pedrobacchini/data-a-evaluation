import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { MessageService } from 'primeng/components/common/api';

@Injectable()
export class ErrorHandlerService {

  constructor(private messageService: MessageService) {
  }

  handle(errorResponse: any) {
    let msg: string;

    if (typeof errorResponse === 'string') {
      msg = errorResponse;
    } else if (errorResponse instanceof HttpErrorResponse
      && errorResponse.status >= 400 && errorResponse.status <= 499) {
      msg = 'Houve um erro ao processar sua requisição';
      try {
        msg = errorResponse.error.friendlyMessage;
      } catch (e) {
      }
      console.error('Ocorreu um erro', errorResponse);
    } else {
      msg = 'Erro ao processar o serviço remoto. Tente novamente.';
      console.log('Ocorreu um erro', errorResponse);
    }

    this.messageService.add({severity: 'error', detail: msg});
  }
}
