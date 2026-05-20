import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AppShell } from '@shared/layout';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';

@Component({
  selector: 'app-admin',
  imports: [AppShell, ButtonModule, CardModule, TagModule, RouterLink],
  templateUrl: './admin.html',
  styleUrl: './admin.scss',
})
export class Admin {}
