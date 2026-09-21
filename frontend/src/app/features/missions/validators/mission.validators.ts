import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator di GRUPPO: controlla che la data di fine sia successiva alla data
 * di inizio. Rispecchia il CHECK del DB:
 * CONSTRAINT ck_mission_planned_dates CHECK (planned_end_at > planned_start_at)
 */
export function dateRangeValidator(startKey: string, endKey: string): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const start = group.get(startKey)?.value;
    const end = group.get(endKey)?.value;

    if (!start || !end) {
      return null;
    }

    const startDate = new Date(start);
    const endDate = new Date(end);

    return endDate > startDate ? null : { dateRangeInvalid: true };
  };
}
