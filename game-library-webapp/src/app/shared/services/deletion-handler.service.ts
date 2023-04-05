import {Injectable} from '@angular/core';
import {ConfirmModalComponent} from '../components/confirm-modal/confirm-modal.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Creator} from '../../model/creator.model';
import {ModelEnum} from '../../model/enum/model.enum';
import {ModelInterface} from '../../model/interface/model.interface';
import {Person} from '../../model/interface/person.interface';
import {ImpersonalInterface} from '../../model/interface/impersonal.interface';

@Injectable({providedIn: 'root'})
export class DeletionHandlerService {

  constructor(private modalService: NgbModal) {
  }

  callModal(modelAsEnum: ModelEnum, associatedModel: ModelInterface, isAPerson?: boolean): Promise<any> {
    let objectType = '';
    let objectName = '';

    switch (modelAsEnum) {
      case ModelEnum.CONTACT: {
        if (isAPerson) {
          objectType = 'les coordonnées associées à cette personne';
          objectName = (associatedModel as Person).firstName + ' ' + (associatedModel as Person).lastName;
        } else {
          objectType = 'les coordonnées associées à cette entité';
          objectName = (associatedModel as ImpersonalInterface).name;
        }
        break;
      }
      case ModelEnum.CREATOR: {
        objectType = 'l\'auteur';
        objectName = (associatedModel as Person).firstName + ' ' + (associatedModel as Creator).lastName;
        break;
      }
      case ModelEnum.THEME: {
        objectType = 'le mécanisme';
        objectName = (associatedModel as ImpersonalInterface).name;
        break;
      }
      case ModelEnum.PUBLISHER: {
        objectType = 'l\'éditeur';
        objectName = (associatedModel as ImpersonalInterface).name;
        break;
      }
      case ModelEnum.PRODUCT_LINE: {
        objectType = 'la gamme';
        objectName = (associatedModel as ImpersonalInterface).name;
        break;
      }
      case ModelEnum.CATEGORY: {
        objectType = 'la catégorie';
        objectName = (associatedModel as ImpersonalInterface).name;
        break;
      }
      case ModelEnum.GAME: {
        objectType = 'Le jeu';
        objectName = (associatedModel as ImpersonalInterface).name;
        break;
      }
    }

    const modalRef = this.modalService.open(ConfirmModalComponent);
    modalRef.componentInstance.deletedObjectType = objectType;
    modalRef.componentInstance.deletedObjectName = objectName;
    return modalRef.result;
  }
}
