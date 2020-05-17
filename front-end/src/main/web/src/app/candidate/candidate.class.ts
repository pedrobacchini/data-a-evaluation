export class Candidate {
  uuid: string;
  name: string;
  picture;
  electionPosition: ElectionPositionCandidate = new ElectionPositionCandidate();
}

export class ElectionPositionCandidate {
  uuid: string;
  name: string;
  election: ElectionCandidate = new ElectionCandidate();
}

export class ElectionCandidate {
  uuid: string;
  name: string;
  startDate: string;
  finishDate: string;
}
