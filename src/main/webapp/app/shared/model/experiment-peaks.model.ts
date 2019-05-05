import { IAnalyte } from 'app/shared/model/analyte.model';
import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';

export interface IExperimentPeaks {
    id?: number;
    peakNumber?: number;
    peakStart?: number;
    peakEnd?: number;
    peakHighest?: number;
    peakArea?: number;
    analyteConcentration?: number;
    analyte?: IAnalyte;
    experimentAnalysis?: IExperimentAnalysis;
}

export class ExperimentPeaks implements IExperimentPeaks {
    constructor(
        public id?: number,
        public peakNumber?: number,
        public peakStart?: number,
        public peakEnd?: number,
        public peakHighest?: number,
        public peakArea?: number,
        public analyteConcentration?: number,
        public analyte?: IAnalyte,
        public experimentAnalysis?: IExperimentAnalysis
    ) {}
}
