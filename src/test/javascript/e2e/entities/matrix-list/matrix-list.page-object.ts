import { element, by, ElementFinder } from 'protractor';

export class MatrixListComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-matrix-list div table .btn-danger'));
    title = element.all(by.css('jhi-matrix-list div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MatrixListUpdatePage {
    pageTitle = element(by.id('jhi-matrix-list-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    matrixNameInput = element(by.id('field_matrixName'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMatrixNameInput(matrixName) {
        await this.matrixNameInput.sendKeys(matrixName);
    }

    async getMatrixNameInput() {
        return this.matrixNameInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class MatrixListDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-matrixList-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-matrixList'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
