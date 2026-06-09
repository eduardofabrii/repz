import { Directive, ElementRef, HostListener, inject } from '@angular/core';
import { formatarTelefone } from './cpf-cnpj';

/**
 * Attribute directive: add [phoneMask] to any <input> bound with [(ngModel)] to
 * automatically format the value as (XX) XXXX-XXXX or (XX) XXXXX-XXXX.
 */
@Directive({
  selector: '[phoneMask]',
  standalone: true,
})
export class PhoneMaskDirective {
  private readonly el = inject(ElementRef<HTMLInputElement>);
  private _busy = false;

  @HostListener('input')
  onInput(): void {
    if (this._busy) return;
    const input = this.el.nativeElement;
    const formatted = formatarTelefone(input.value);
    if (formatted === input.value) return;
    this._busy = true;
    input.value = formatted;
    // Re-dispatch so Angular's ngModel picks up the formatted value
    input.dispatchEvent(new Event('input', { bubbles: true }));
    this._busy = false;
  }
}
