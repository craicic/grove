import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'enumToString'})
export class StringEnumPipe implements PipeTransform {

  transform(value): any {
    switch (value) {
      case 'AUTHOR':
        return 'Auteur';
      case 'AUTHOR_ILLUSTRATOR':
        return 'Auteur illustrateur';
      case 'DESIGNER':
        return 'Designer';
      case 'ILLUSTRATOR':
        return 'Illustrateur';
      case 'TOY':
        return 'Jouet';
      case 'BOARD_GAME':
        return 'Jeu de société';
      case 'BIG_GAME':
        return 'Grand jeu';
      case 'OVERSIZE_GAME':
        return 'Jeu surdimensionné';
      case 'WOODEN_GAME':
        return 'Jeu en bois';
    }
  }
}
