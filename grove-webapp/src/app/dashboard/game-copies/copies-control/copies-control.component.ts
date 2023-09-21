import {Component, Input, OnInit} from '@angular/core';
import {GameCopy} from '../../../model/game-copy.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-copies-control',
  templateUrl: './copies-control.component.html',
  styleUrls: ['./copies-control.component.css']
})
export class CopiesControlComponent implements OnInit {
  @Input() copies: GameCopy[] = [];
  showDetail = false;
  current: GameCopy;


  constructor(private router: Router) {
  }

  ngOnInit(): void {
    console.table(this.copies);
  }

  onDisplay(copy: GameCopy): void {
    this.showDetail = !this.showDetail;
    this.current = copy;
  }

  onNew(): void {
  }
}
