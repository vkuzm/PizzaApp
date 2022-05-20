import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AppUrls} from "../../appUrls";
import {ContactService} from "../../services/contact.service";
import {MetaService} from "../../services/meta.service";

@Component({
  selector: 'app-component-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {
  contactForm: FormGroup;
  contactSuccess: boolean;
  contactError: boolean;

  constructor(private appUrls: AppUrls,
              private contactService: ContactService,
              private formBuilder: FormBuilder) {
  }

  private initContactForm() {
    this.contactForm = this.formBuilder.group({
      firstname: [null, Validators.required],
      lastname: [null, Validators.required],
      message: [null, Validators.required],
    });
  }

  onSubmit() {
    this.contactError = false;

    if (this.contactForm.valid) {
      if (this.contactService.sendEmail(this.contactForm.getRawValue())) {
        this.resetAndUpdate();
      } else {
        this.contactError = true;
      }

    } else {
      this.validateAllFormFields(this.contactForm);
    }
  }

  private resetAndUpdate() {
    this.contactForm.reset();
    this.contactSuccess = true;
  }

  private validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);

      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});

      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }

    });
  }

  private isFieldValid(field: string) {
    return !this.contactForm.get(field).valid && this.contactForm.get(field).touched;
  }

  private displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }

  ngOnInit() {
    this.initContactForm();
  }

}
