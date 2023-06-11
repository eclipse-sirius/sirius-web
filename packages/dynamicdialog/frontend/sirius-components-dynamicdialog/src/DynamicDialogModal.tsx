/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { gql, useLazyQuery, useMutation, useQuery } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import ErrorIcon from '@material-ui/icons/Error';
import React, { useEffect, useState } from 'react';
import {
  DynamicDialogModalProps,
  GQLApplyDynamicDialogMutationData,
  GQLApplyDynamicDialogVariables,
  GQLDSelectWidget,
  GQLDynamicDialog,
  GQLErrorPayload,
  GQLGetDynamicDialogQueryData,
  GQLGetDynamicDialogQueryVariables,
  GQLGetDynamicDialogValidationMessagesQueryData,
  GQLGetDynamicDialogValidationMessagesQueryVariables,
  GQLValidationMessages,
  GQLVariable,
} from './DynamicDialogModal.types';
import { DSelectPropertySection } from './widgets/DSelectPropertySection';
import { DTextFieldPropertySection } from './widgets/DTextFieldPropertySection';

const applyDialogMutation = gql`
  mutation executeDialogMutation($input: ApplyDynamicDialogInput!) {
    applyDynamicDialog(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getDynamicDialogQuery = gql`
  query getDynamicDialog($editingContextId: ID!, $objectId: ID!, $dialogDescriptionId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        dynamicDialog(objectId: $objectId, dialogDescriptionId: $dialogDescriptionId) {
          id
          label
          widgets {
            __typename
            id
            descriptionId
            parentId
            outputVariableName
            inputVariableNames
            required
            label
            initialValue
          }
        }
      }
    }
  }
`;

const getValidationMessagesQuery = gql`
  query getValidationMessages(
    $editingContextId: ID!
    $objectId: ID!
    $dialogDescriptionId: ID!
    $variables: [Variable]
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        dynamicDialogValidationMessages(
          objectId: $objectId
          dialogDescriptionId: $dialogDescriptionId
          variables: $variables
        ) {
          severity
          message
          blocksApplyDialog
        }
      }
    }
  }
`;

const useDynamicDialogModalStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
  validationMessage: {
    display: 'flex',
    padding: `${theme.spacing(1)} ${theme.spacing(2)}`,
  },
}));

export const DynamicDialogModal = ({
  editingContextId,
  dialogDescriptionId,
  objectId,
  onMutationDone,
  onClose,
}: DynamicDialogModalProps) => {
  const classes = useDynamicDialogModalStyles();

  // STATES
  const [message, setMessage] = useState('');
  const [dynamicDialog, setDynamicDialog] = useState(null as GQLDynamicDialog);
  const [allWidgetVariableValues, setAllWidgetVariablesValue] = useState(new Map<string, string>());
  const [allWidgetVariablesSetValue, setAllWidgetVariablesSetValue] = useState(new Map<string, any>());
  const [validationMessages, setValidationMessages] = useState(new Array<GQLValidationMessages>());

  // QUERY FOR DYNAMIC DIALOG
  const {
    loading: dialogLoading,
    data: dialogData,
    error: dialogError,
  } = useQuery<GQLGetDynamicDialogQueryData, GQLGetDynamicDialogQueryVariables>(getDynamicDialogQuery, {
    variables: { editingContextId, objectId, dialogDescriptionId },
  });

  // const allWidgetVariables = new Map<string, VariableRuntime>();
  useEffect(() => {
    if (!dialogLoading) {
      if (dialogError) {
        setMessage(dialogError.message);
      }
      const dynDialog = dialogData?.viewer?.editingContext?.dynamicDialog;
      if (dynDialog) {
        setDynamicDialog(dynDialog);

        const newAllWidgetVariableValues: Map<string, string> = new Map(allWidgetVariableValues);
        const newAllWidgetVariablesSetValue: Map<string, any> = new Map(allWidgetVariablesSetValue);
        dynDialog.widgets?.map((widget) => {
          if (widget.__typename === 'DSelectWidget') {
            newAllWidgetVariableValues.set(widget.outputVariableName, (widget as GQLDSelectWidget).initialValue);
          }
          const setValue = (outputVariableName, value) => {
            setAllWidgetVariablesValue((prevValue) => {
              const newAllWidgetVariableValues: Map<string, string> = new Map(prevValue);
              newAllWidgetVariableValues.set(outputVariableName, value);

              //get validation messages
              const widgetVariables = Array<GQLVariable>();
              newAllWidgetVariableValues.forEach((value, name) => {
                widgetVariables.push({ name, value });
              });

              getValidationMessages({
                variables: { editingContextId, objectId, dialogDescriptionId, variables: widgetVariables },
              });

              return newAllWidgetVariableValues;
            });
          };
          newAllWidgetVariablesSetValue.set(widget.outputVariableName, setValue);
        });
        setAllWidgetVariablesValue(newAllWidgetVariableValues);
        setAllWidgetVariablesSetValue(newAllWidgetVariablesSetValue);
      }
    }
  }, [dialogLoading, dialogData, dialogError]);

  // QUERY FOR VALAIDATION MESSAGE
  const [getValidationMessages, { loading: messagesLoading, data: messagesData, error: messagesError }] = useLazyQuery<
    GQLGetDynamicDialogValidationMessagesQueryData,
    GQLGetDynamicDialogValidationMessagesQueryVariables
  >(getValidationMessagesQuery, {
    variables: { editingContextId, objectId, dialogDescriptionId, variables: [] },
  });

  useEffect(() => {
    if (!messagesLoading) {
      if (messagesError) {
        setMessage(applyDialogError.message);
      }
      const messages = messagesData?.viewer?.editingContext?.dynamicDialogValidationMessages;
      if (messages) {
        setValidationMessages(messages);
      }
    }
  }, [messagesLoading]);

  // MUTATION
  const [applyDialog, { loading: applyDialogLoading, data: applyDialogData, error: applyDialogError }] =
    useMutation<GQLApplyDynamicDialogMutationData>(applyDialogMutation);
  useEffect(() => {
    if (!applyDialogLoading) {
      if (applyDialogError) {
        setMessage(applyDialogError.message);
      }
      if (applyDialogData) {
        if (applyDialogData.applyDynamicDialog.__typename === 'ErrorPayload') {
          const errorPayload = applyDialogData.applyDynamicDialog as GQLErrorPayload;
          setMessage(errorPayload.message);
        } else {
          onClose();
        }
      }
    }
  }, [applyDialogLoading, applyDialogData, applyDialogError, onClose]);

  const onApplyDialog = () => {
    const widgetVariables = Array();
    allWidgetVariableValues.forEach((value, name) => {
      widgetVariables.push({ name, value });
    });

    const variables: GQLApplyDynamicDialogVariables = {
      input: {
        id: crypto.randomUUID(),
        dialogDescriptionId: dialogData.viewer.editingContext.dynamicDialog.id,
        editingContextId,
        objectId,
        widgetVariables,
      },
    };
    applyDialog({ variables });
  };

  // VALIDATION
  const isOKEnabled = () => {
    const dialogDescription = dialogData?.viewer?.editingContext?.dynamicDialog;
    let valid: boolean = !dialogLoading && !message && dialogDescription !== null && dialogDescription !== undefined;
    if (valid) {
      dialogDescription.widgets.map((widget) => {
        // all widget output that are required must be effectively set.
        if (widget.required) {
          valid = valid && !!allWidgetVariableValues.get(widget.outputVariableName);
        }
      });
    }
    if (valid) {
      valid = validationMessages?.find((validationMessage) => validationMessage.blocksApplyDialog) === undefined;
    }
    return valid;
  };

  //RENDERING
  // const toto = (
  //   <DSelectPropertySection
  //     widgetId="id"
  //     label="label"
  //     editingContextId="edictx"
  //     objectId="objectId"
  //     selectedObject="selec"
  //     inputVariables={new Map()}
  //     setSelectedObject={null}></DSelectPropertySection>
  // );

  const widgetComponents = dynamicDialog?.widgets.map((gqlWidget) => {
    let dSelectWidget;
    if (gqlWidget.__typename === 'DSelectWidget') {
      dSelectWidget = DSelectPropertySection;
    } else if (gqlWidget.__typename === 'DTextFieldWidget') {
      dSelectWidget = DTextFieldPropertySection;
    }
    // const gqlSelectWidget = gqlWidget as GQLDSelectWidget;
    const inputVariables = Array();

    gqlWidget.inputVariableNames?.forEach((name) => {
      inputVariables.push({ name: name, value: allWidgetVariableValues.get(name) });
    });
    inputVariables.push({ name: 'self', value: objectId });

    return React.createElement(dSelectWidget, {
      key: gqlWidget.id,
      dialogDescriptionId: gqlWidget.parentId,
      widgetDescriptionId: gqlWidget.id,
      outputVariableName: gqlWidget.outputVariableName,
      label: gqlWidget.label,
      editingContextId,
      initialValue: allWidgetVariableValues.get(gqlWidget.outputVariableName),
      setValue: allWidgetVariablesSetValue.get(gqlWidget.outputVariableName),
      inputVariables,
    });
  });

  // messagesData?.viewer?.editingContext?.dynamicDialogValidationMessages;
  // const validationMessagesComponents = messagesData?.viewer?.editingContext?.dynamicDialogValidationMessages?.map(
  //   (validationMessage) => {
  //     return <Typography variant="subtitle2">{validationMessage.message}</Typography>;
  //   }
  // );
  const validationMessagesComponents = validationMessages?.map((validationMessage) => {
    const validationComponent = (
      <div className={classes.validationMessage}>
        <ErrorIcon></ErrorIcon>
        <Typography variant="subtitle2">{validationMessage.message}</Typography>
      </div>
    );
    return validationComponent;
  });

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">{dynamicDialog?.label}</DialogTitle>
        <DialogContent>
          <div className={classes.form}>{validationMessagesComponents}</div>
        </DialogContent>
        <DialogContent>
          <div className={classes.form}>{widgetComponents}</div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={!isOKEnabled()}
            data-testid="create-object"
            color="primary"
            onClick={onApplyDialog}>
            Create
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setMessage(null)}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setMessage(null)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
