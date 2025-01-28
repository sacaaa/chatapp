import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
    selector: 'app-navbar',
    imports: [CommonModule],
    templateUrl: './navbar.component.html'
})
export class NavbarComponent {
    menuOpen = false;
    isLoggedIn: boolean;

    menuItems = [
        { label: 'Rooms', path: 'chatrooms', requiresLogin: false },
    ];

    ctaItems = [
        {
            label: 'Login',
            path: '/login',
            onlyWhenLoggedOut: true,
            isButton: false,
        },
        {
            label: 'Register',
            path: '/register',
            onlyWhenLoggedOut: true,
            isButton: false,
        },
        {
            label: 'Profile',
            path: '/profile',
            onlyWhenLoggedIn: true,
            isButton: false,
        },
        {
            label: 'Logout',
            action: () => this.authService.logout(),
            onlyWhenLoggedIn: true,
            isButton: true,
        },
    ];

    constructor(
        private router: Router,
        private authService: AuthService,
    ) {
        this.isLoggedIn = this.authService.isLoggedIn();
    }

    toggleMenu() {
        this.menuOpen = !this.menuOpen;
    }

    getVisibleCTAItems() {
        return this.ctaItems.filter(
            (item) =>
                (item.onlyWhenLoggedIn && this.authService.isLoggedIn()) ||
                (item.onlyWhenLoggedOut && !this.authService.isLoggedIn()),
        );
    }

    getVisibleMenuItems() {
        return this.menuItems.filter(
            (item) => !item.requiresLogin || this.authService.isLoggedIn(),
        );
    }
}
