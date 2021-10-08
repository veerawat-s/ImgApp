import { Injectable } from '@angular/core';
import { ImgComp } from './img-comp';
import { IMGS } from './mock-img-comps'
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ImgCompService {

  private targetUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  getImages(): Observable<ImgComp[]> {
    const imgs = of(IMGS);
    return imgs;
  }

  getTodayImages(): Observable<ImgComp[]> {
    return this.http.get<ImgComp[]>(this.targetUrl + 'memory');
  }

  deleteImage(img: ImgComp) : Observable<string> {
    return this.http.delete<string>(this.targetUrl + 'delete/' + img.id);
  }
}
