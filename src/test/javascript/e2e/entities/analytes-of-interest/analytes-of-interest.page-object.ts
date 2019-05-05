import { element, by, ElementFinder } from 'protractor';

export class AnalytesOfInterestComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-analytes-of-interest div table .btn-danger'));
    title = element.all(by.css('jhi-analytes-of-interest div h2#page-heading span')).first();

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

export class AnalytesOfInterestUpdatePage {
    pageTitle = element(by.id('jhi-analytes-of-interest-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    methodSelect = element(by.id('field_method'));
    analyteSelect = element(by.id('field_analyte'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async methodSelectLastOption() {
        await this.methodSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async methodSelectOption(option) {
        await this.methodSelect.sendKeys(option);
    }

    getMethodSelect(): ElementFinder {
        return this.methodSelect;
    }

    async getMethodSelectedOption() {
        return this.methodSelect.element(by.css('option:checked')).getText();
    }

    async analyteSelectLastOption() {
        await this.analyteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async analyteSelectOption(option) {
        await this.analyteSelect.sendKeys(option);
    }

    getAnalyteSelect(): ElementFinder {
        return this.analyteSelect;
    }

    async getAnalyteSelectedOption() {
        return this.analyteSelect.element(by.css('option:checked')).getText();
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

export class AnalytesOfInterestDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-analytesOfInterest-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-analytesOfInterest'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
