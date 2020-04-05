import { ElectionPosition } from './election-position.class';


export class Election {
  uuid: string;
  name: string;
  startDate: string;
  finishDate: string;
  electionPositions: ElectionPosition[] = [];
}
