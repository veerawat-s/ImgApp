import { Component, OnInit } from '@angular/core';
import { ImgComp } from '../img-comp';
import { IMGS } from '../mock-img-comps';
import { ImgCompService } from '../img-comp.service';

@Component({
  selector: 'app-imagecomps',
  templateUrl: './imagecomps.component.html',
  styleUrls: ['./imagecomps.component.css']
})
export class ImagecompsComponent implements OnInit {
  images:ImgComp[] = [];
  selectedImg?: ImgComp;

  constructor(private imgCompService: ImgCompService) { }

  ngOnInit(): void {
    this.getTodayImages();
  }

  onSelect(image: ImgComp): void {
    this.selectedImg = image;
  }

  getImages(): void {
    this.imgCompService.getImages()
      .subscribe(images => this.images = images );
  }

  getTodayImages(): void {
    this.imgCompService.getTodayImages()
      .subscribe(images => this.images = images );
  }

  deleteImage(): void {
    this.imgCompService.deleteImage(this.selectedImg).subscribe(response => console.log(response));
  }

}
