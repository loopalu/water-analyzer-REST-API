import { IExperimentPeaks } from 'app/shared/model/experiment-peaks.model';
import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';

export interface IAnalyte {
    id?: number;
    analyteName?: string;
    analyteGroup?: string;
    experimentPeaks?: IExperimentPeaks[];
    analytesOfInterests?: IAnalytesOfInterest[];
}

export class Analyte implements IAnalyte {
    constructor(
        public id?: number,
        public analyteName?: string,
        public analyteGroup?: string,
        public experimentPeaks?: IExperimentPeaks[],
        public analytesOfInterests?: IAnalytesOfInterest[]
    ) {}
}
