import { IMethod } from 'app/shared/model/method.model';

export interface ICapillaryType {
    id?: number;
    capillaryName?: string;
    capillaryLength?: number;
    effectiveLength?: number;
    innerDiameter?: number;
    outerDiameter?: number;
    electricForce?: number;
    methods?: IMethod[];
}

export class CapillaryType implements ICapillaryType {
    constructor(
        public id?: number,
        public capillaryName?: string,
        public capillaryLength?: number,
        public effectiveLength?: number,
        public innerDiameter?: number,
        public outerDiameter?: number,
        public electricForce?: number,
        public methods?: IMethod[]
    ) {}
}
