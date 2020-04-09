export class Election {
  uuid: string;
  name: string;
  startDate: string;
  finishDate: string;
  electionPositions: ElectionPosition[] = [];
}

export class ElectionPosition {
  uuid?: string;
  name: string;
  candidates?: CandidateElection[] = [];
}

export class CandidateElection {
  uuid: string;
  name: string;
  picture: string;
}

