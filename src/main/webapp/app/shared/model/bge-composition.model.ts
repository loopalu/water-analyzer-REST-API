import { IMethod } from 'app/shared/model/method.model';

export interface IBGEComposition {
    id?: number;
    backgroundElectrolyte?: string;
    bgeConcentration?: number;
    method?: IMethod;
}

export class BGEComposition implements IBGEComposition {
    constructor(public id?: number, public backgroundElectrolyte?: string, public bgeConcentration?: number, public method?: IMethod) {}
}
