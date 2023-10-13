import {Component, OnInit} from '@angular/core';
import {GameService} from '../../game.service';
import {Router} from '@angular/router';
import {Game} from '../../../../model/game.model';
import {ImageService} from '../../../../shared/services/image.service';

@Component({
  selector: 'app-image-handler',
  templateUrl: './image-handler.component.html',
  styleUrls: ['./image-handler.component.css']
})
export class ImageHandlerComponent implements OnInit {

  game: Game;
  file: File;

  constructor(private service: ImageService,
              private gameService: GameService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.game = this.gameService.getDetailedGame();
  }

  onUpload(): void {
    this.service.uploadImage(this.file, this.game.id)
      .subscribe(image => {
          this.resetInput();
          this.service.updateImages(image);
        },
        err => console.log(err)
      );
  }

  onRemove(): void {
    this.resetInput();
  }

  onBack(): void {
    this.resetInput();
    this.router.navigate(['/admin/lib/games' + this.gameService.game.id + '/edit']);
  }

  onFileSelected(event: any): void {
    this.file = event.target.files[0];
  }

  returnFileSize(bytes: number): string {
    if (bytes < 1024) {
      return `${bytes} bytes`;
    } else if (bytes >= 1024 && bytes < 1048576) {
      return `${(bytes / 1024).toFixed(1)} KB`;
    } else if (bytes >= 1048576) {
      return `${(bytes / 1048576).toFixed(1)} MB`;
    }
  }

  resetInput(): void {
    this.file = null;
    (document.getElementById('inputFile') as HTMLInputElement).value = '';
  }
}
