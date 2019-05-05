import { element, by, ElementFinder } from 'protractor';

export class ExperimentResultsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-experiment-results div table .btn-danger'));
    title = element.all(by.css('jhi-experiment-results div h2#page-heading span')).first();

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

export class ExperimentResultsUpdatePage {
    pageTitle = element(by.id('jhi-experiment-results-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    measuringPointInput = element(by.id('field_measuringPoint'));
    voltageValueInput = element(by.id('field_voltageValue'));
    experimentSelect = element(by.id('field_experiment'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMeasuringPointInput(measuringPoint) {
        await this.measuringPointInput.sendKeys(measuringPoint);
    }

    async getMeasuringPointInput() {
        return this.measuringPointInput.getAttribute('value');
    }

    async setVoltageValueInput(voltageValue) {
        await this.voltageValueInput.sendKeys(voltageValue);
    }

    async getVoltageValueInput() {
        return this.voltageValueInput.getAttribute('value');
    }

    async experimentSelectLastOption() {
        await this.experimentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async experimentSelectOption(option) {
        await this.experimentSelect.sendKeys(option);
    }

    getExperimentSelect(): ElementFinder {
        return this.experimentSelect;
    }

    async getExperimentSelectedOption() {
        return this.experimentSelect.element(by.css('option:checked')).getText();
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

export class ExperimentResultsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-experimentResults-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-experimentResults'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
