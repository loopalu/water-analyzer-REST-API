import { IMethod } from 'app/shared/model/method.model';
import { IAnalyte } from 'app/shared/model/analyte.model';

export interface IAnalytesOfInterest {
    id?: number;
    method?: IMethod;
    analyte?: IAnalyte;
}

export class AnalytesOfInterest implements IAnalytesOfInterest {
    constructor(public id?: number, public method?: IMethod, public analyte?: IAnalyte) {}
}
