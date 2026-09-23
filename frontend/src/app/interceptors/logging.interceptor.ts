import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class LoggingInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('🔵 REQUEST:', request.method, request.url);

    return next.handle(request).pipe(
      tap((event) => {
        if (event instanceof HttpResponse) {
          console.log('🟢 RESPONSE:', request.method, request.url, event.status, event.body);
        }
      }),
      catchError((error: HttpErrorResponse) => {
        console.error('🔴 ERROR:', request.method, request.url, error.status, error.message);
        console.error('Error details:', error);
        throw error;
      })
    );
  }
}
