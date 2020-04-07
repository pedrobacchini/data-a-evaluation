import { Candidate } from '../candidate/candidate.class';

export class ElectionPosition {
  uuid?: string;
  name: string;
  candidates?: Candidate[] = [];
}
