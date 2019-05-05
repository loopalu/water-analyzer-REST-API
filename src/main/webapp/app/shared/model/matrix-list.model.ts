import { IMethod } from 'app/shared/model/method.model';

export interface IMatrixList {
    id?: number;
    matrixName?: string;
    methods?: IMethod[];
}

export class MatrixList implements IMatrixList {
    constructor(public id?: number, public matrixName?: string, public methods?: IMethod[]) {}
}
