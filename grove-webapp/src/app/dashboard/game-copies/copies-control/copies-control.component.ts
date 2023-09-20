import {Component, Input, OnInit} from '@angular/core';
import {GameCopy} from '../../../model/game-copy.model';

@Component({
  selector: 'app-copies-control',
  templateUrl: './copies-control.component.html',
  styleUrls: ['./copies-control.component.css']
})
export class CopiesControlComponent implements OnInit {
  @Input() copies: GameCopy[] = [];
  showDetail = false;
  current: GameCopy;

  ngOnInit(): void {
    console.table(this.copies);
  }

  onDisplay(copy: GameCopy): void {
    this.showDetail = !this.showDetail;
    this.current = copy;
  }
}
