import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProfileResponse } from '../models/profile-response';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root',
})
export class ProfileService {
    private readonly baseUrl = 'http://localhost:8080/api/users';

    constructor(
        private http: HttpClient,
        private authService: AuthService,
    ) {}

    getProfile(): Observable<any> {
        const id = this.authService.getIdFromToken();
        if (!id) {
            return new Observable((observer) =>
                observer.error('No user ID available'),
            );
        }

        return this.http.get<ProfileResponse>(this.baseUrl + `/${id}`);
    }
}
