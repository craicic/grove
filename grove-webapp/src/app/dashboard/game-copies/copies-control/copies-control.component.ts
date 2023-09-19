import {Component, Input, OnInit} from '@angular/core';
import {GameCopy} from '../../../model/game-copy.model';

@Component({
  selector: 'app-copies-control',
  templateUrl: './copies-control.component.html',
  styleUrls: ['./copies-control.component.css']
})
export class CopiesControlComponent implements OnInit {
  @Input() copies: GameCopy[] = [];
  ngOnInit(): void {
    console.table(this.copies);
  }
}
