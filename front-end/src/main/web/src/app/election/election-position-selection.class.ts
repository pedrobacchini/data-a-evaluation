import { ElectionPositionCandidate } from '../candidate/candidate.class';

export class ElectionPositionSelection {
  uuid: string;
  name: string;

  static build(electionPosition: ElectionPositionCandidate): ElectionPositionSelection {
    const electionPositionSelection = new ElectionPositionSelection();
    electionPositionSelection.uuid = electionPosition.uuid;
    electionPositionSelection.name = electionPosition.name;
    return electionPositionSelection;
  }
}
