import { element, by, ElementFinder } from 'protractor';

export class ExperimentAnalysisComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-experiment-analysis div table .btn-danger'));
    title = element.all(by.css('jhi-experiment-analysis div h2#page-heading span')).first();

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

export class ExperimentAnalysisUpdatePage {
    pageTitle = element(by.id('jhi-experiment-analysis-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    measuringPointInput = element(by.id('field_measuringPoint'));
    voltageExperimentInput = element(by.id('field_voltageExperiment'));
    voltageSmoothedInput = element(by.id('field_voltageSmoothed'));
    valueMovingAverageSubtractedInput = element(by.id('field_valueMovingAverageSubtracted'));
    valueOverThresholdInput = element(by.id('field_valueOverThreshold'));
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

    async setVoltageExperimentInput(voltageExperiment) {
        await this.voltageExperimentInput.sendKeys(voltageExperiment);
    }

    async getVoltageExperimentInput() {
        return this.voltageExperimentInput.getAttribute('value');
    }

    async setVoltageSmoothedInput(voltageSmoothed) {
        await this.voltageSmoothedInput.sendKeys(voltageSmoothed);
    }

    async getVoltageSmoothedInput() {
        return this.voltageSmoothedInput.getAttribute('value');
    }

    async setValueMovingAverageSubtractedInput(valueMovingAverageSubtracted) {
        await this.valueMovingAverageSubtractedInput.sendKeys(valueMovingAverageSubtracted);
    }

    async getValueMovingAverageSubtractedInput() {
        return this.valueMovingAverageSubtractedInput.getAttribute('value');
    }

    async setValueOverThresholdInput(valueOverThreshold) {
        await this.valueOverThresholdInput.sendKeys(valueOverThreshold);
    }

    async getValueOverThresholdInput() {
        return this.valueOverThresholdInput.getAttribute('value');
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

export class ExperimentAnalysisDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-experimentAnalysis-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-experimentAnalysis'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
