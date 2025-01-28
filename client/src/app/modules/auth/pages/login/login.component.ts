import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { Router } from '@angular/router';
import { AuthResponse } from '../../../../core/models/auth-response';

@Component({
    selector: 'app-login',
    imports: [ReactiveFormsModule, CommonModule],
    templateUrl: './login.component.html',
})
export class LoginComponent {
    loginForm: FormGroup;
    formSubmitted: boolean = false;

    constructor(
        private fb: FormBuilder,
        private authService: AuthService,
        private router: Router,
    ) {
        this.loginForm = this.fb.group({
            emailOrNickname: ['', [Validators.required]],
            password: ['', [Validators.required, Validators.minLength(6)]],
            rememberMe: [false],
        });
    }

    onLogin(event: Event) {
        event.preventDefault();
        this.formSubmitted = true;
        if (this.loginForm.invalid) {
            return;
        }

        const { emailOrNickname, password, rememberMe } = this.loginForm.value;
        this.authService
            .login(emailOrNickname, password, rememberMe)
            .subscribe({
                next: (response: AuthResponse) => {
                    console.log('Login successful', response);
                    this.router.navigate(['/']);
                },
                error: (error) => {
                    console.error('Login failed', error);
                },
            });
    }
}
