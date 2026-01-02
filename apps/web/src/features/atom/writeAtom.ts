import { atom } from 'jotai';
import { Interview } from '../../entities/events/model/Interview';

export const selectedDateAtom = atom<Date | null>(new Date());
export const currentMonthAtom = atom<Date>(new Date());
export const eventsAtom = atom<{ [key: string]: Interview[] }>({});