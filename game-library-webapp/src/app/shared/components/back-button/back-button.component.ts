import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-back-button',
  templateUrl: './back-button.component.html',
  styleUrls: ['./back-button.component.css']
})
export class BackButtonComponent implements OnInit {

  @Output()
  backEvent = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  onBack(): void {
    this.backEvent.emit();
  }
}
