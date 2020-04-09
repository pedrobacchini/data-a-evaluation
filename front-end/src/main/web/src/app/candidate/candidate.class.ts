export class Candidate {
  uuid: string;
  name: string;
  picture: string;
  electionPosition: ElectionPositionCandidate;
}

export class ElectionPositionCandidate {
  uuid: string;
  name: string;
  election: ElectionCandidate;
}

export class ElectionCandidate {
  uuid: string;
  name: string;
}
