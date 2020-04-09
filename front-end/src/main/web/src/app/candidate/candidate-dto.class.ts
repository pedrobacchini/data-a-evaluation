export class CandidateDTO {
  uuid: string;
  name: string;
  picture: string;
  electionPosition: ElectionPositionDTO;
}

export class ElectionPositionDTO {
  uuid: string;
  name: string;
  election: ElectionDTO;
}

export class ElectionDTO {
  uuid: string;
  name: string;
}
