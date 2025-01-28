import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  registerForm: FormGroup;
  formSubmitted: boolean = false;
  selectedAvatarId: number = 1;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group(
      {
        avatarId: [this.selectedAvatarId],
        email: ['', [Validators.required, Validators.email]],
        nickname: ['', [Validators.required]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
        termsAndConditions: [false, Validators.requiredTrue]
      },
      { validators: this.passwordMatchValidator }
    );
  }

  onRegister(event: Event) {
    event.preventDefault();
    this.formSubmitted = true;
    if (this.registerForm.invalid) {
      return;
    }

    const {email, nickname, password} = this.registerForm.value;
    this.authService.register(email, nickname, this.selectedAvatarId, password).subscribe({
      next: (response) => {
          console.log('Registration successful', response);
          this.router.navigate(['/login']);
      },
      error: (error) => {
          console.error('Registration failed', error);
      },
  });
  }

  private passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');
    if (
        password &&
        confirmPassword &&
        password.value !== confirmPassword.value
    ) {
        return { passwordMismatch: true };
    }
    return null;
  }

}
