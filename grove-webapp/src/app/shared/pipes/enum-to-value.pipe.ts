import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'enumToValue'
})
export class EnumToValuePipe<T extends { [enumKey: string]: any }> implements PipeTransform {

  transform(value: any, enumName: T): string {
    return enumName[value];
  }
}
