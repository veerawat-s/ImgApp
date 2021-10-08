import { Component, OnInit } from '@angular/core';
import { ImgComp } from '../img-comp';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-img-form',
  templateUrl: './img-form.component.html',
  styleUrls: ['./img-form.component.css']
})
export class ImgFormComponent implements OnInit {

  targetUrl = "http://localhost:8080/upload";
  uploadForm: FormGroup;
  imgComp: ImgComp

  constructor(private formBuilder: FormBuilder, private http: HttpClient) { }

  ngOnInit(): void {
    this.uploadForm = this.formBuilder.group({
      title: [''],
      description: [''],
      image: ['']
    });
    this.imgComp = {id:null, date:null,description:null,title:null,imageUrl:null};
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.uploadForm.get('image').setValue(file);
    }
  }

  newForm() {
    this.imgComp = {id:null, date:null,description:null,title:null,imageUrl:null};
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('title',this.imgComp.title);
    formData.append('description',this.imgComp.description);
    formData.append('image', this.uploadForm.get('image').value);

    this.http.post<any>(this.targetUrl, formData).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );
  }

}
