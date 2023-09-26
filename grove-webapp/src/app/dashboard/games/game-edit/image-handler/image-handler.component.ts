import {Component, OnInit} from '@angular/core';
import {GameService} from '../../game.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Game} from '../../../../model/game.model';
import {ImageService} from '../../../../shared/services/image.service';
import {map, mergeMap} from 'rxjs/operators';
import {logger} from 'codelyzer/util/logger';
import {Subscription} from 'rxjs';
import {Image} from '../../../../model/image.model';

@Component({
    selector: 'app-image-handler',
    templateUrl: './image-handler.component.html',
    styleUrls: ['./image-handler.component.css']
})
export class ImageHandlerComponent implements OnInit {

    game: Game;
    file: File;
    private newId: number;

    constructor(private service: ImageService,
                private gameService: GameService,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit(): void {
        this.game = this.gameService.getDetailedGame();
    }

    onUpload(): void {

        console.log(this.file);
        this.service.uploadImage(this.file, this.game.id)
            .subscribe(imageId => {
                    const fr: FileReader = new FileReader();
                    let imageContent;
                    fr.readAsDataURL(this.file);
                    fr.onload = () => {
                         imageContent = fr.result;
                    };
                    const image: Image = {id: imageId, content: imageContent};
                    this.service.updateImagesSubject(image);
                },
                err => console.log(err)
            );
    }

    onRemove(): void {
        this.resetInput();
    }

    onBack(): void {
        this.resetInput();
        this.router.navigate(['/admin/locked-mode/games/' + this.gameService.game.id + '/edit']);
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
