import { Component, computed, effect, inject, input, output, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '@core/services/auth';
import { LayoutService } from '@core/services/layout';
import { ButtonModule } from 'primeng/button';

export interface NavItem {
  key: string;
  label: string;
  link: string;
  icon: string;
  /** Roles permitidas — espelha o guard da rota. */
  roles: string[];
}

export interface NavSection {
  key: string;
  label: string;
  icon: string;
  items: NavItem[];
}

const NAV_SECTIONS: NavSection[] = [
  {
    key: 'admin',
    label: 'Admin',
    icon: 'pi-shield',
    items: [
      { key: 'admin-dash', label: 'Painel admin', link: '/admin', icon: 'pi-home', roles: ['ADMIN'] },
      { key: 'academias', label: 'Academias', link: '/admin/academias', icon: 'pi-building', roles: ['ADMIN'] },
      { key: 'admin-personais', label: 'Personais', link: '/admin/personais', icon: 'pi-users', roles: ['ADMIN'] },
      { key: 'admin-alunos', label: 'Alunos', link: '/admin/alunos', icon: 'pi-id-card', roles: ['ADMIN'] },
    ],
  },
  {
    key: 'academia',
    label: 'Academia',
    icon: 'pi-building',
    items: [
      { key: 'academia-dash', label: 'Painel academia', link: '/academia', icon: 'pi-home', roles: ['GERENTE', 'ADMIN'] },
      { key: 'planos', label: 'Planos', link: '/academia/planos', icon: 'pi-tag', roles: ['GERENTE', 'ADMIN'] },
      { key: 'academia-personais', label: 'Personais', link: '/academia/personais', icon: 'pi-users', roles: ['GERENTE', 'ADMIN'] },
      { key: 'academia-alunos', label: 'Alunos', link: '/academia/alunos', icon: 'pi-id-card', roles: ['GERENTE', 'ADMIN'] },
    ],
  },
  {
    key: 'personal',
    label: 'Personal',
    icon: 'pi-user-edit',
    items: [
      { key: 'personal-dash', label: 'Painel personal', link: '/personal', icon: 'pi-home', roles: ['PERSONAL', 'ADMIN'] },
    ],
  },
  {
    key: 'aluno',
    label: 'Aluno',
    icon: 'pi-user',
    items: [
      { key: 'dashboard', label: 'Dashboard', link: '/aluno', icon: 'pi-home', roles: ['ALUNO', 'ADMIN'] },
      { key: 'treino', label: 'Meu treino', link: '/aluno/ficha-treino', icon: 'pi-list', roles: ['ALUNO', 'ADMIN'] },
      { key: 'frequencia', label: 'Frequência', link: '/aluno/frequencia', icon: 'pi-calendar', roles: ['ALUNO', 'ADMIN'] },
      { key: 'evolucao', label: 'Evolução', link: '/aluno/evolucao', icon: 'pi-chart-line', roles: ['ALUNO', 'ADMIN'] },
      { key: 'perfil', label: 'Perfil', link: '/aluno/perfil', icon: 'pi-user', roles: ['ALUNO', 'ADMIN'] },
    ],
  },
];

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, ButtonModule],
  templateUrl: './app-sidebar.html',
  styleUrl: './app-sidebar.scss',
  host: { '[class.is-collapsed]': 'layout.colapsada()' },
})
export class AppSidebar {
  protected readonly layout = inject(LayoutService);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  readonly nome = input<string>('Usuário');
  readonly subtitulo = input<string>('');
  readonly ativo = input<string>('');

  readonly ctaTitulo = input<string>('Bora treinar!');
  readonly ctaDescricao = input<string>('Registre sua presença e mantenha a sequência.');
  readonly ctaLabel = input<string>('Fazer check-in');
  readonly mostrarCta = input<boolean>(true);

  readonly ctaClick = output<void>();

  /** Seções visíveis (filtradas pelo role) — uma seção é exibida se tem ≥1 item permitido. */
  readonly secoes = computed<NavSection[]>(() => {
    const role = this.auth.sessao()?.role ?? this.auth.getUserRole() ?? '';
    return NAV_SECTIONS.map((s) => ({
      ...s,
      items: s.items.filter((i) => i.roles.includes(role)),
    })).filter((s) => s.items.length > 0);
  });

  /** Quais seções estão expandidas. Default: só a que contém o item ativo. */
  private readonly expandido = signal<Record<string, boolean>>({});

  constructor() {
    // Quando o item ativo muda, abre automaticamente a seção dele.
    effect(() => {
      const ativoKey = this.ativo();
      const sec = this.secoes().find((s) => s.items.some((i) => i.key === ativoKey));
      if (sec) {
        this.expandido.update((m) => ({ ...m, [sec.key]: true }));
      }
    });
  }

  isExpandida(key: string): boolean {
    return this.expandido()[key] ?? false;
  }

  toggleSecao(key: string): void {
    this.expandido.update((m) => ({ ...m, [key]: !(m[key] ?? false) }));
  }

  readonly inicial = computed(() => (this.nome().trim()[0] ?? 'U').toUpperCase());

  /** Limpa sessão e volta para a tela de login. */
  logout(): void {
    this.auth.logout();
    this.router.navigate(['/auth']);
  }
}
