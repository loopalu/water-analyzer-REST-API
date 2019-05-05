import { IExperiment } from 'app/shared/model/experiment.model';

export interface IExperimentResults {
    id?: number;
    measuringPoint?: number;
    voltageValue?: number;
    experiment?: IExperiment;
}

export class ExperimentResults implements IExperimentResults {
    constructor(public id?: number, public measuringPoint?: number, public voltageValue?: number, public experiment?: IExperiment) {}
}
