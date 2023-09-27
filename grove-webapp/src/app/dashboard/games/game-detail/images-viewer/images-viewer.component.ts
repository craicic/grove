import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, of, Subscription} from 'rxjs';
import {Image} from '../../../../model/image.model';
import {ImageService} from '../../../../shared/services/image.service';
import {environment} from '../../../../../environments/environment';

@Component({
    selector: 'app-images-viewer',
    templateUrl: './images-viewer.component.html',
    styleUrls: ['./images-viewer.component.css']
})
export class ImagesViewerComponent implements OnInit, OnDestroy {
    private subscription: Subscription;
    images: Observable<Image[]>;
    filePrefix: string;

    constructor(public service: ImageService) {
        this.filePrefix = environment.filePrefix;
    }

    ngOnInit(): void {
        this.subscription = this.service.images$.subscribe(i => {
            this.images = of(i);
        });
    }

    ngOnDestroy(): void {
        console.log('destroyed Image Viewer');
        this.subscription.unsubscribe();
    }


}
