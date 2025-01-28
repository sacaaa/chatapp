import { Component } from '@angular/core';
import { ProfileResponse } from '../../../../core/models/profile-response';
import { ProfileService } from '../../../../core/services/profile.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile',
  imports: [CommonModule],
  templateUrl: './profile.component.html'
})
export class ProfileComponent {
  profileData: ProfileResponse | null = null;
  isLoading: boolean = true;
  errorMessage: string | null = null;

  constructor(private profileService: ProfileService) {}

  ngOnInit(): void {
      this.loadProfile();
  }

  loadProfile(): void {
      this.profileService.getProfile().subscribe({
          next: (data) => {
            data.createdAt = data.createdAt.replace('T', ' ').substring(0, 19);
              this.profileData = data;
              this.isLoading = false;
              console.log('Profile data:', data);
          },
          error: (error) => {
              console.error('Error loading profile:', error);
              this.errorMessage = 'Failed to load profile data.';
              this.isLoading = false;
          },
      });
  }
}
