import { element, by, ElementFinder } from 'protractor';

export class AnalyteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-analyte div table .btn-danger'));
    title = element.all(by.css('jhi-analyte div h2#page-heading span')).first();

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

export class AnalyteUpdatePage {
    pageTitle = element(by.id('jhi-analyte-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    analyteNameInput = element(by.id('field_analyteName'));
    analyteGroupInput = element(by.id('field_analyteGroup'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAnalyteNameInput(analyteName) {
        await this.analyteNameInput.sendKeys(analyteName);
    }

    async getAnalyteNameInput() {
        return this.analyteNameInput.getAttribute('value');
    }

    async setAnalyteGroupInput(analyteGroup) {
        await this.analyteGroupInput.sendKeys(analyteGroup);
    }

    async getAnalyteGroupInput() {
        return this.analyteGroupInput.getAttribute('value');
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

export class AnalyteDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-analyte-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-analyte'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
