import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, of, Subscription} from 'rxjs';
import {Mechanism} from '../../../../model/mechansim.model';
import {Image} from '../../../../model/image.model';
import {Game} from '../../../../model/game.model';
import {GameService} from '../../game.service';
import {ImageService} from '../../../../shared/services/image.service';
import {environment} from '../../../../../environments/environment';

@Component({
    selector: 'app-images-viewer',
    templateUrl: './images-viewer.component.html',
    styleUrls: ['./images-viewer.component.css']
})
export class ImagesViewerComponent implements OnInit, OnDestroy {
    private game: Game;
    private subscription: Subscription;
    images: Observable<Image[]>;
    filePrefix: string;

    constructor(private gameService: GameService,
                public service: ImageService) {
        this.filePrefix = environment.filePrefix;
    }

    ngOnInit(): void {
        this.subscription = this.service.imagesSubject$.subscribe(i => {
            console.log('init Image Viewer');
            this.images = of(i);
        });
    }

    ngOnDestroy(): void {
        console.log('destroyed Image Viewer');
        this.subscription.unsubscribe();
    }


}
