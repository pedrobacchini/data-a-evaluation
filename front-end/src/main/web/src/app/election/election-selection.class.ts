import { ElectionCandidate } from '../candidate/candidate.class';

export class ElectionSelection {
  uuid: string;
  name: string;

  static build(candidateElection: ElectionCandidate): ElectionSelection {
    const electionSelection = new ElectionSelection();
    electionSelection.uuid = candidateElection.uuid;
    electionSelection.name = candidateElection.name;
    return electionSelection;
  }
}
