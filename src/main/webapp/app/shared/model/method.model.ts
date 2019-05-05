import { IExperiment } from 'app/shared/model/experiment.model';
import { IBGEComposition } from 'app/shared/model/bge-composition.model';
import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';
import { ICapillaryType } from 'app/shared/model/capillary-type.model';
import { IMatrixList } from 'app/shared/model/matrix-list.model';

export interface IMethod {
    id?: number;
    methodName?: string;
    frequency?: number;
    injectionType?: string;
    injectionTime?: number;
    measureValue?: number;
    unitOfMeasure?: string;
    experimentTime?: number;
    current?: number;
    experiments?: IExperiment[];
    bGECompositions?: IBGEComposition[];
    analytesOfInterests?: IAnalytesOfInterest[];
    capillaryType?: ICapillaryType;
    matrixList?: IMatrixList;
}

export class Method implements IMethod {
    constructor(
        public id?: number,
        public methodName?: string,
        public frequency?: number,
        public injectionType?: string,
        public injectionTime?: number,
        public measureValue?: number,
        public unitOfMeasure?: string,
        public experimentTime?: number,
        public current?: number,
        public experiments?: IExperiment[],
        public bGECompositions?: IBGEComposition[],
        public analytesOfInterests?: IAnalytesOfInterest[],
        public capillaryType?: ICapillaryType,
        public matrixList?: IMatrixList
    ) {}
}
