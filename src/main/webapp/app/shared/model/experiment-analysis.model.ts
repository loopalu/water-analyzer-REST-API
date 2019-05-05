import { IExperimentPeaks } from 'app/shared/model/experiment-peaks.model';
import { IExperiment } from 'app/shared/model/experiment.model';

export interface IExperimentAnalysis {
    id?: number;
    measuringPoint?: number;
    voltageExperiment?: number;
    voltageSmoothed?: number;
    valueMovingAverageSubtracted?: number;
    valueOverThreshold?: number;
    experimentPeaks?: IExperimentPeaks[];
    experiment?: IExperiment;
}

export class ExperimentAnalysis implements IExperimentAnalysis {
    constructor(
        public id?: number,
        public measuringPoint?: number,
        public voltageExperiment?: number,
        public voltageSmoothed?: number,
        public valueMovingAverageSubtracted?: number,
        public valueOverThreshold?: number,
        public experimentPeaks?: IExperimentPeaks[],
        public experiment?: IExperiment
    ) {}
}
