import { element, by, ElementFinder } from 'protractor';

export class ExperimentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-experiment div table .btn-danger'));
    title = element.all(by.css('jhi-experiment div h2#page-heading span')).first();

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

export class ExperimentUpdatePage {
    pageTitle = element(by.id('jhi-experiment-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    experimentTimeInput = element(by.id('field_experimentTime'));
    experimentTypeInput = element(by.id('field_experimentType'));
    methodSelect = element(by.id('field_method'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setExperimentTimeInput(experimentTime) {
        await this.experimentTimeInput.sendKeys(experimentTime);
    }

    async getExperimentTimeInput() {
        return this.experimentTimeInput.getAttribute('value');
    }

    async setExperimentTypeInput(experimentType) {
        await this.experimentTypeInput.sendKeys(experimentType);
    }

    async getExperimentTypeInput() {
        return this.experimentTypeInput.getAttribute('value');
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

export class ExperimentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-experiment-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-experiment'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
