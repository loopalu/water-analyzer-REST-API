import { Moment } from 'moment';
import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';
import { IExperimentResults } from 'app/shared/model/experiment-results.model';
import { IMethod } from 'app/shared/model/method.model';

export interface IExperiment {
    id?: number;
    experimentTime?: Moment;
    experimentType?: string;
    experimentAnalyses?: IExperimentAnalysis[];
    experimentResults?: IExperimentResults[];
    method?: IMethod;
}

export class Experiment implements IExperiment {
    constructor(
        public id?: number,
        public experimentTime?: Moment,
        public experimentType?: string,
        public experimentAnalyses?: IExperimentAnalysis[],
        public experimentResults?: IExperimentResults[],
        public method?: IMethod
    ) {}
}
