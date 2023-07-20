import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'objectToString'})
export class ObjectToStringPipe implements PipeTransform {

  transform(value): any {
    const stringArray: string[] = [];
    value.forEach(object => stringArray.push(object.name));
    return stringArray;
  }
}
